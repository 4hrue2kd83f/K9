package com.blamejared.mcbot.commands;

import java.awt.Color;
import java.util.Random;

import com.blamejared.mcbot.commands.api.Command;
import com.blamejared.mcbot.commands.api.CommandBase;
import com.blamejared.mcbot.commands.api.CommandContext;
import com.blamejared.mcbot.commands.api.CommandException;
import com.blamejared.mcbot.commands.api.CommandRegistrar;
import com.blamejared.mcbot.listeners.CommandListener;

import sx.blah.discord.util.EmbedBuilder;

@Command
public class CommandCommands extends CommandBase {
    
    public CommandCommands() {
        super("commands", false);
    }
    
    private final Random rand = new Random();
    
    @Override
    public void process(CommandContext ctx) throws CommandException {
        final StringBuilder builder = new StringBuilder();
        final String prefix = CommandListener.prefixes.get(ctx);
        CommandRegistrar.INSTANCE.getCommands().forEach((key, val) -> {
            if (val.requirements().matches(ctx.getMessage().getAuthor(), ctx.getGuild())) {
                builder.append(prefix).append(key).append("\n");
            }
        });
        rand.setSeed(builder.toString().hashCode());
        EmbedBuilder embed = new EmbedBuilder()
                .withDesc(builder.toString())
                .withColor(Color.HSBtoRGB(rand.nextFloat(), 1, 1))
                .withTitle("Commands Available:");
        ctx.reply(embed.build());
    }
    
    public String getDescription() {
        return "Displays all commands";
    }
}
