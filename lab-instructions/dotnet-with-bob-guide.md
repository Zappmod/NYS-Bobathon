# Building a Sample .NET Application with IBM Bob

A step-by-step lab guide for creating a sample ASP.NET Core Web API application using **IBM Bob** — IBM's AI-powered IDE assistant.

---

## Prerequisites

Before starting, make sure you have the following installed:

| Requirement | Details |
|---|---|
| **IBM Bob IDE** | See the [Bob Installation Guide](https://bob.ibm.com/docs/ide/getting-started/install) |
| **.NET SDK 8+** | Download from [dotnet.microsoft.com](https://dotnet.microsoft.com/download) |
| **An empty project folder** | A local folder on your machine for the new project |

---

## Phase 1 — Set Up Your Workspace in Bob

### Step 1 — Open Your Project Folder in Bob
Open IBM Bob IDE and use **File → Open Folder** to open the empty folder you created for your .NET project. Bob works within the context of an open workspace folder.

### Step 2 — Open the Agentic Chat Sidebar
The **agentic chat sidebar** is your primary interface for working with Bob. It allows Bob to read files, reason across your codebase, and implement features end-to-end. Open it from the sidebar or panel area in the Bob IDE.

---

## Phase 2 — Plan the Application

### Step 3 — Switch to Plan Mode
In the chat input area, click the **mode selector dropdown** and choose **Plan**. Plan mode is designed for planning and architecture — use it before writing any code.

> 💡 **Bob Best Practice:** Always start with Plan mode for new projects. Plan mode helps Bob generate a detailed implementation plan before any code is written.

### Step 4 — Describe Your .NET Application to Bob
In the chat, enter a prompt describing what you want to build. Be specific — the more detail you provide, the better the plan. Example:

```text
I want to create a sample ASP.NET Core Web API application using .NET 8 or greater.
It should have a single controller called ProductsController that supports
CRUD operations (GET all, GET by ID, POST, PUT, DELETE) for a Product model
with fields: Id (int), Name (string), Price (decimal), and InStock (bool).
Use in-memory data storage with a few pre-seeded sample products so the API
returns data immediately without needing to POST first. Please create a detailed
implementation plan.
```

> 💡 **Tip:** Specifying **.NET 8 or greater** ensures the plan stays compatible whether you have .NET 8, 9, or 10 installed. Requesting **pre-seeded sample data** means the GET endpoints will return results right away when you test in Phase 7.

### Step 5 — Review the Plan
Before generating the plan, Bob may ask clarifying questions about your preferences — for example, whether to use a specific folder structure, naming conventions, or error-handling style. Feel free to answer and customize to your liking, or simply accept Bob's recommended defaults to keep things moving.

Once the plan is generated, **read it carefully** to confirm it matches your goals. You can ask Bob to revise the plan if anything is missing or incorrect — while still in Plan mode, no code changes are made.

---

## Phase 3 — Initialize the Project Context

### Step 6 — Start a New Conversation
Once you are satisfied with the plan, **start a new context window** (new conversation). This gives Bob a clean, focused context for implementation without the planning discussion mixed in.

> 💡 **Why this matters:** Bob is stateless between conversations. Starting fresh avoids context poisoning — where earlier planning discussion could confuse the implementation phase.

### Step 7 — Switch to Agent Mode
Select **Agent** from the mode selector. Agent mode is designed for writing, modifying, and refactoring code — it is the mode you will use for implementation.

### Step 8 — Initialize the Project with `/init`
Type the following command in the chat:

```text
/init
```

Bob will scan your (currently empty) folder and create an `AGENTS.md` file and a `.bob/` folder with mode-specific context files. This gives Bob persistent memory about your project across future conversations. Click **Approve** when Bob asks permission to write these files.

---

## Phase 4 — Scaffold the .NET Application

### Step 9 — Ask Bob to Create the Project Structure
Prompt Bob to scaffold the application:

```text
Create a new ASP.NET Core Web API project called SampleDotNetApp targeting .NET 8 or greater.
Scaffold the project files including Program.cs, a Product model, a ProductsController with
full CRUD operations using an in-memory list, and the .csproj file. Pre-seed the in-memory
store with at least 3 sample products so the GET endpoints return data immediately.
```

Bob will propose each file. For each file Bob creates:
- Click **Save** to write the file to your project.
- Review the diff before accepting if you want to inspect the content first.

### Step 10 — Review and Approve Each File
Bob will typically create files in this order:

| File | Purpose |
|---|---|
| `SampleDotNetApp.csproj` | Project definition and dependencies |
| `Program.cs` | App entry point and middleware setup |
| `Models/Product.cs` | The Product data model |
| `Controllers/ProductsController.cs` | CRUD API endpoints |

Approve each file as Bob proposes it.

---

## Phase 5 — Explore and Understand the Code

### Step 11 — Switch to Ask Mode
Change the mode selector to **Ask**. Ask mode is read-only — Bob can explain code without making changes.

### Step 12 — Ask Bob to Explain the Code
Use prompts like these to understand what was built:

```text
Explain how dependency injection is configured in Program.cs.
```
```text
Walk me through the GET /api/products/{id} endpoint in ProductsController.
```
```text
What would I need to change to use a real database instead of in-memory storage?
```

---

## Phase 6 — Extend and Iterate

### Step 13 — Add More Features (Agent Mode)
Switch back to **Agent mode** and ask Bob to extend the application. Example prompts:

```text
Add input validation to the POST and PUT endpoints so that Name cannot be
empty and Price must be greater than 0. Return a 400 Bad Request with details
if validation fails.
```

```text
Add Swagger/OpenAPI support to this project so I can test the API from a browser.
```

### Step 14 — Add a Custom Rules File (Optional but Recommended)
To standardize Bob's behavior for this project, add a rules file. In Agent mode:

```text
Create a file at .bob/rules/dotnet_rules.md with the following rules:
- Always use .NET 8 and C# 12 syntax
- Follow RESTful API naming conventions
- Use async/await for all I/O operations
- Include XML documentation comments on all public methods
```

Bob will create the file. These rules will apply to all future conversations in this project.

---

## Phase 7 — Validate the Application

### Step 15 — Build the Project
In Agent mode, ask Bob to verify the project builds and confirm the port:

```text
Check that the project builds successfully with `dotnet build`. Then check launchSettings.json
(or Program.cs) to confirm which port the app will listen on, and give me the exact
`dotnet run` command I should paste into my terminal to start it.
```

Bob will run `dotnet build` to catch any compilation errors and report the port. It will then provide the ready-to-run command for you to paste.

### Step 16 — Test the API Endpoints
Once running, use `curl`, Postman, or the Swagger UI (if added in Step 13) to test your endpoints:

```bash
# Get all products
curl http://localhost:5000/api/products

# Get a single product by ID
curl http://localhost:5000/api/products/1

# Create a product
curl -X POST http://localhost:5000/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Widget","price":9.99,"inStock":true}'

# Update a product
curl -X PUT http://localhost:5000/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"Widget Pro","price":14.99,"inStock":true}'

# Delete a product
curl -X DELETE http://localhost:5000/api/products/1
```

---

## Summary of Bob's Mode Strategy

| Mode | When to Use |
|---|---|
| **Plan** | Designing and planning architecture before writing code |
| **Agent** | Implementing features, creating files, running commands |
| **Ask** | Exploring and understanding existing code — no file changes |

---

## Key Bob Concepts to Remember

- **Always review before approving** — Bob asks for permission before reading or writing files. Review what it proposes.
- **`/init` is your friend** — Run it at the start of every project so Bob has persistent context.
- **Start new conversations between phases** — Avoids context buildup that can degrade output quality.
- **Specific prompts = better results** — "Create an ASP.NET Core 8 Web API with a ProductsController supporting CRUD" works far better than "build a .NET app."
- **Edit `AGENTS.md` manually** if you want to add business rules or conventions that Bob's automated scan won't detect.
