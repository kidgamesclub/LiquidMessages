package club.kidgames.liquid.plugin

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LiquifyCommand(val runtime:LiquidRuntime) : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<String>): Boolean {
    if (args.isEmpty()) {
      return false
    }
    var template = args.joinToString(" ").trim()
    if (!template.startsWith("{{")) {
      template = "{{$template}}"
    }

    val resolved = when(sender) {
      is Player-> runtime.render(template, sender)
      else-> runtime.render(template)
    }

    when (sender) {
      is Player-> sender.sendRawMessage("Resolved message: '$resolved'")
      else-> sender.sendMessage("Resolved message: '$resolved'")
    }

    return true
  }
}