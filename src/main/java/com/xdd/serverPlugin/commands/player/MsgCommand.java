package com.xdd.serverPlugin.commands.player;

import com.xdd.serverPlugin.messageSystem.MessageActions;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "msg", aliases = {"message", "wiadomosc", "wiad", "pv"})
public class MsgCommand {

    @Execute
    void execute(@Context Player sender, @Arg Player receiver, @Arg String[] strings){
        MessageActions.sendMessage(sender, receiver, strings);
    }
}
