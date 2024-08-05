//package org.snezhok69.Commands;
//
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.snezhok69.DifferentMethods.Variables;
//
//import java.util.Map;
//
//public class CommandArgs implements CommandExecutor {
//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        try {
//            if (args.length == 0) {
//                Map<String, Map<String, Object>> commands = Variables.getInstance().getDescription().getCommands();
//                if (commands.containsKey(label.toLowerCase())) {
//                    CommandRtp.commandRtp(sender);
//                    return true;
//                }
//                sender.sendMessage(Variables.pluginName + " §cInvalid command!");
//                return false;
//            }
//            String subCommand = args[0].toLowerCase();
//            switch (subCommand) {
//                // ARGUMENT ON \\
//                case "on":
//                    if (args.length < 2) {
//                        CommandOn.commandReload(sender);
//                    } else {
//                        sender.sendMessage(Variables.pluginName + " §cInvalid command!");
//                    }
//                    break;
//                // ARGUMENT OFF \\
//                case "off":
//                    if (args.length < 2) {
//                        CommandOff.commandnear(sender);
//                    } else {
//                        sender.sendMessage(Variables.pluginName + " §cInvalid command!");
//                    }
//                    break;
//                // ARGUMENT reload \\
//                case "reload":
//                    if (args.length < 2) {
//                        CommandReload.commandnear(sender);
//                    } else {
//                        sender.sendMessage(Variables.pluginName + " §cInvalid command!");
//                    }
//                    break;
//                // ARGUMENT version \\
//                case "version":
//                    if (args.length < 2) {
//                        CommandVersion.commandnear(sender);
//                    } else {
//                        sender.sendMessage(Variables.pluginName + " §cInvalid command!");
//                    }
//                    break;
//            }
//            return true;
//        } catch (Throwable e) {
//            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//            String callingClassName = stackTrace[2].getClassName();
//        }
//        return false;
//    }
//}

