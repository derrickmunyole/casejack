# Case Management Platform

A case management system built in Java/Spring Boot, modeled on a
multi-tenant, metadata-driven architecture.

## Why this project exists

This project lets tenants define
their own objects and fields at runtime, without code changes or redeployment. The
build proceeds in phases, starting with a plain single-tenant CRUD baseline and
progressively introducing multi-tenancy, a metadata engine, dynamic storage, and
automation — mirroring (at a simplified scale) how multitenant systems are
architected.

## Tech Stack

- **Java** / **Spring Boot 4.1.0**
- **Gradle** (build tool)
- **PostgreSQL** (database)
- **Lombok** (boilerplate reduction — introduced after the initial hand-written pass)
- **JUnit 5** + **Mockito** (testing)

## Getting Started

### Prerequisites
- JDK (version matching your `build.gradle` toolchain)
- Docker (for running Postgres locally), or a local Postgres install

### Run Postgres locally
```bash
docker run --name case-mgmt-db \
  -e POSTGRES_PASSWORD=<your-local-password> \
  -e POSTGRES_DB=casemgmt \
  -p 5432:5432 \
  -d postgres
```

### Configure the application
Database credentials are **not** committed to this repo. Set them as environment
variables before running:
```bash
export DB_USERNAME=postgres
export DB_PASSWORD=<your-local-password>
```
`application.properties` reads these via `${DB_USERNAME}`/`${DB_PASSWORD}` — see
that file for the full datasource configuration.

### Run the application
```bash
./gradlew bootRun
```
The app starts on `http://localhost:8080`.

### Run tests
```bash
./gradlew test
```

## Multi-Tenancy

Every request must include an `X-Tenant-ID` header identifying the tenant making
the request:
```bash
curl http://localhost:8080/api/cases -H "X-Tenant-ID: your-tenant-id"
```
Requests without this header are rejected with a `400` before reaching any
endpoint. Tenant isolation is enforced end-to-end: a tenant can only read,
update, or delete its own cases — requests for another tenant's case return a
`404`, not a `403`, so as not to reveal whether a given id exists at all.

Tenant scoping is applied at the application layer (shared schema, discriminator
column) rather than via separate databases or schemas per tenant — every table
carries a `tenant_id` column, and every repository query is explicitly scoped
by it.

## API Overview

Base path: `/api/cases`

All endpoints below require an `X-Tenant-ID` header (see Multi-Tenancy above).

| Method | Path | Description |
|---|---|---|
| POST | `/api/cases` | Create a case |
| GET | `/api/cases` | List all cases for the current tenant |
| GET | `/api/cases/{id}` | Get a case by id (must belong to the current tenant) |
| PUT | `/api/cases/{id}` | Update a case (must belong to the current tenant) |
| DELETE | `/api/cases/{id}` | Delete a case (must belong to the current tenant) |

Validation failures return `400` with per-field error messages. A missing case,
or a case belonging to a different tenant, returns `404`. A missing
`X-Tenant-ID` header returns `400`. Malformed JSON returns `400` with a generic
parse-error message. All error responses share a consistent shape (`timestamp`,
`status`, `error`, `message`, `path`).

## Project Status

**Phase 1 (complete):** Plain CRUD baseline — entity, repository, DTOs
(request/response split), service layer, controller, Bean Validation, global
exception handling, and a full unit + web-layer test suite (Mockito + MockMvc).

**Phase 2 (complete):** Multi-tenancy — request-scoped tenant context
(`ThreadLocal` + servlet filter), tenant-scoped repository queries, and full
test coverage including cross-tenant isolation checks at both the service and
controller layers.

**Coming up:**
- Phase 3 — Metadata engine (tenant-defined objects/fields)
- Phase 4 — Dynamic data storage
- Phase 5 — Query abstraction layer
- Phase 6 — Automation layer (validation rules + triggers)
- Phase 7 — Security & sharing model
- Phase 9 — Cross-cutting concerns (logging, config profiles)
- Phase 10 — AWS deployment (SAM/CloudFormation) — secondary/bonus focus