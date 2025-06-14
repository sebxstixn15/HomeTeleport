# HomeTeleporter

**HomeTeleporter** is a Bukkit/Spigot Minecraft plugin that lets players create, teleport to, list, rename, and remove multiple named homes. Operators can configure the global teleport cooldown for the `/home` command.

---

## 📝 Approach

- **Data Storage:** Each player’s homes are saved as YML files under `plugins/HomeTeleporter/players/`. The global config (e.g., teleport cooldown) is in `plugins/HomeTeleporter/config.yml`.
- **Singleton PlayerManager:** Core logic for saving, loading, teleporting, deleting, and renaming homes is centralized in a singleton manager.
- **Cooldown:** A global cooldown for `/home` is enforced and can be set by server operators.
- **Player-Only Commands:** Commands are designed to be used in-game, not via console.
- **Operator Rights:** Certain commands (e.g., setting the timer) require OP privileges.

---

## ⚡ Installation & Setup

1. **Installation**
   - Place the compiled plugin JAR file into your server’s `plugins/` directory.
   - Generate a Folder called `HomeTeleporter` in `plugins/` directory in your server
   - Generate a Folder called `players` in `plugins/HomeTeleporter` directory in your server

3. **Configuration**
   - If you first use the `/home` command or `/admin setTimer [int]` it generated the config.yml in `plugins/HomeTeleporter/` by default, the teleport cooldown is 5 seconds.
   - You can adjust this in `plugins/HomeTeleporter/config.yml` or by using the `/admin setTimer <seconds>` command in-game (OP required).

4. **Permissions**
   - All players can use home commands.
   - Only server operators (OP) can use `/admin setTimer`.

---

## 🕹️ Commands

| Command                         | Description                                                  | Permission       |
|:---------------------------------|:-------------------------------------------------------------|:-----------------|
| `/sethome [name]`                | Save your current location as a home (default: "home")       | All players      |
| `/home [name]`                   | Teleport to a home (default: "home", cooldown enforced)      | All players      |
| `/removehome [name]`             | Delete a saved home (default: "home")                        | All players      |
| `/listhomes`                     | List all your homes and their coordinates                    | All players      |
| `/renamehome <old> <new>`        | Rename an existing home                                      | All players      |
| `/admin setTimer <seconds>`      | Set the `/home` cooldown timer (global, OP only)             | Operator (OP)    |

---

## ✨ Extra Features

- **Multiple named homes** per player.
- **Home listing** with coordinates.
- **Rename and remove homes** by name.
- **Configurable cooldown** by OPs at runtime or file.

---

## ✅ Testing

1. Start your server with the plugin installed.
2. Join as a regular player:
    - Use `/sethome`, `/home`, `/removehome`, `/listhomes`, `/renamehome`.
    - Try `/home` multiple times to see the cooldown enforcement.
3. Join as OP (operator):
    - Use `/admin setTimer <seconds>` and verify cooldown changes in-game.
4. Check the generated files under `plugins/HomeTeleporter/players/`.

---

## 🔧 Troubleshooting

- Ensure your server and the `plugins/HomeTeleporter/players/` directory are writable.
- Only OPs can use admin commands.
- For errors (saving, teleporting), check your server console for logs.

---

Enjoy managing your homes with **HomeTeleporter**!