# Claude Code Cheatsheet

## Privacy & Data Sharing

### Does Claude Code Share Your Code?

**Yes** - Claude Code sends prompts and code to Anthropic's servers to process requests (required for AI to work).

### Data Retention

| User Type | Default Retention | Zero Retention Option |
|-----------|------------------|----------------------|
| **Consumer** (Free/Pro/Max) | 30 days (opted out) or 5 years (opted in for training) | ❌ Not available |
| **Commercial** (Team/Enterprise/API) | 30 days | ✅ Available (via API key config) |

### Model Training

- **Commercial users:** Code is NOT used for training (unless you opt into Development Partner Program)
- **Consumer users:** Can choose via settings (applies to new/resumed chats only, starting Aug 28, 2025)

### Opt-Out Telemetry/Error Reporting

```bash
export STATSIG_ENABLED=false
export SENTRY_ENABLED=false
```

## Excluding Files from Claude's Access

### Deny Permissions (settings.json)

Files matching deny patterns are **completely invisible** to Claude Code:

```json
{
  "permissions": {
    "deny": [
      "Read(./.env)",                    // Environment files
      "Read(./.env.*)",                  // All .env variants
      "Read(./secrets/**)",              // Entire directories
      "Read(**/*.key)",                  // All .key files anywhere
      "Read(./config/credentials.json)", // Specific files
      "Read(./.git/**)",                 // Git internals
      "Read(./node_modules/**)",         // Dependencies
      "Bash(rm -rf *)",                  // Dangerous commands
      "Write(./production.db)"           // Protect specific files from writes
    ]
  }
}
```

**Deny rules take precedence over allow rules.**

### Settings File Locations

1. **`~/.claude/settings.json`** - Global (all projects)
2. **`.claude/settings.json`** - Team-shared (commit to git)
3. **`.claude/settings.local.json`** - Personal (add to .gitignore)

---

## Memory vs Settings

### Memory (CLAUDE.md) - "What to remember"

**Purpose:** Instructions and context Claude should know (natural language)

**Examples:**
- "Prefer tabs over spaces"
- "Use clean architecture pattern"
- "Write tests for all new features"
- "British English spelling"

**Quick Commands:**
```bash
#                            # Start message with # to add memory
/memory                      # Edit memory files directly
/init                        # Bootstrap new CLAUDE.md
```

**Locations (hierarchy, top = highest priority):**
1. **Enterprise** - `C:\ProgramData\ClaudeCode\CLAUDE.md`
2. **User** - `~/.claude/CLAUDE.md` (all your projects)
3. **Project** - `./CLAUDE.md` or `./.claude/CLAUDE.md` (team-shared)

**Import syntax:**
```markdown
@path/to/other.md            # Import file (5 hops max)
```

### Settings (settings.json) - "How to behave"

**Purpose:** Technical configuration, permissions, security (JSON structure)

**Examples:**
```json
{
  "permissions": {
    "deny": ["Read(./.env)"],
    "allow": ["Read(src/**)"]
  },
  "environment": {
    "JAVA_HOME": "/path/to/java"
  },
  "model": "claude-sonnet-4",
  "sandbox": {
    "enabled": true
  }
}
```

**Hierarchy (highest priority first):**
1. Enterprise managed settings
2. Command-line arguments
3. `.claude/settings.local.json` (personal, git-ignored)
4. `.claude/settings.json` (team-shared)
5. `~/.claude/settings.json` (user global)

---

## Chat Session Management

### Resume Previous Conversations

```bash
claude --resume              # Interactive picker (summaries, timestamps, branch)
claude --continue            # Auto-resume most recent conversation
claude --continue -p "text"  # Resume with specific prompt
```

**All conversations automatically saved locally with full message history.**

---

## Interactive Mode

### Starting Claude

```bash
claude                       # Standard interactive session
claude -p "prompt"           # Headless mode with query
claude --permission-mode plan  # Start in read-only Plan Mode
```

### Keyboard Shortcuts

| Key | Action |
|-----|--------|
| `Ctrl+C` | Cancel current input/generation |
| `Ctrl+D` | Exit Claude Code session |
| `Ctrl+L` | Clear terminal screen |
| `Ctrl+O` | Toggle verbose output |
| `Ctrl+R` | Reverse search command history |
| `Ctrl+V` | Paste image from clipboard (Mac/Linux) |
| `Alt+V` | Paste image from clipboard (Windows) |
| `Esc Esc` | Rewind code/conversation |
| `Tab` | Toggle extended thinking mode |
| `Shift+Tab` | Cycle permission modes |
| `Ctrl+B` | Move operation to background |

### Multiline Input

- `\ + Enter` (universal)
- `Option+Enter` (macOS)
- `Shift+Enter` (after `/terminal-setup`)

### Quick Input Shortcuts

| Prefix | Purpose | Example |
|--------|---------|---------|
| `#` | Add to memory (CLAUDE.md) | `#prefer tabs over spaces` |
| `/` | Slash command | `/memory` |
| `!` | Direct bash command | `!git status` |
| `@` | Reference file/directory | `@src/main.kt` |

---

## Permission Modes (Shift+Tab to cycle)

| Mode | Behavior | Indicator |
|------|----------|-----------|
| **Normal** | Interactive editing with confirmations | Default |
| **Auto-Accept** | Automatically applies all edits | `⏵⏵ accept edits on` |
| **Plan** | Read-only analysis, no file changes | `⏸ plan mode on` |

```bash
claude --permission-mode plan   # Start in Plan Mode
```

---

## Custom Slash Commands

### Create Commands

- **Project:** `.claude/commands/mycommand.md`
- **Personal:** `~/.claude/commands/mycommand.md`

### Use Placeholders

```markdown
<!-- mycommand.md -->
Fix issue #$ARGUMENTS in the codebase
```

### Invoke

```bash
/mycommand 123               # Expands with $ARGUMENTS = "123"
```

---

## Output Formats

```bash
claude -p "prompt" --output-format text         # Plain text (default)
claude -p "prompt" --output-format json         # Full conversation log
claude -p "prompt" --output-format stream-json  # Real-time streaming
```

---

## Unix Integration

```bash
cat file.txt | claude -p 'analyze this' > output.txt
claude -p 'summarize' < input.md
```

---

## Useful Workflows

### File/Directory References

```
@filepath                    # Include file content immediately
@dirname                     # Get directory listing
```

### Image Analysis

- Drag and drop images into terminal
- Paste with `Ctrl+V` (Mac/Linux) or `Alt+V` (Windows)

### Parallel Sessions with Git Worktrees

```bash
git worktree add ../project-feature-a -b feature-a
cd ../project-feature-a
claude                       # Independent Claude session
```

### Extended Thinking

- Press `Tab` to toggle
- Or use phrases like "think deeply" or "analyze carefully"

### Background Tasks

- Press `Ctrl+B` to background long operations
- Continue working while processes execute

---

## Security Features

### Sandboxed Bash Execution

```bash
/sandbox                     # Enable filesystem/network isolation
```

### Built-in Protections

- **Write access limited** to project folder only
- **Network requests** require explicit approval
- **Dangerous commands blocked** (e.g., `curl`, `wget` by default)
- **Explicit approval required** for file editing and command execution

### Audit Permissions

```bash
/permissions                 # Review current permission settings
```

---

## Vim Mode (optional)

```bash
/vim                         # Enable vim keybindings
```

**Basic vim commands:**
- **Navigation:** `h/j/k/l`, `w`, `b`, `0`, `$`
- **Editing:** `x`, `dd`, `cc`, `.`
- **Switch:** `Esc` (normal), `i` (insert)

---

## Tips & Best Practices

### Memory (CLAUDE.md)
- Use bullet points under descriptive headings
- Keep information current and relevant
- Review periodically
- Import depth supports up to 5 recursive hops

### Security
- Use deny permissions for sensitive files
- Review all proposed changes before approval
- Use project-specific settings for sensitive repos
- Regularly audit permissions with `/permissions`

### Performance
- Enable "Search and Reference Chats" in settings for context from past conversations
- Use git worktrees for parallel isolated sessions
- Background long operations with `Ctrl+B`

### Context Management
- Conversation picker shows: summaries, timestamps, message count, git branch
- `@` references load files immediately without waiting for Claude
- Extended thinking (`Tab`) for complex problems
