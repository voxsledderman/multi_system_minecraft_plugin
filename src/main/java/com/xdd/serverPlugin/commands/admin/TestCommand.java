package com.xdd.serverPlugin.commands.admin;

import com.xdd.serverPlugin.ServerPlugin;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "test")
@Permission("test.kod")
public class TestCommand {
    private final ServerPlugin plugin = ServerPlugin.getInstance();

    @Execute
    void test(@Context Player player){

    }
}
