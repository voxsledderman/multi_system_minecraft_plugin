package com.xdd.serverPlugin.interfaces;

import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

public interface Stoppable {
    //Implementors must have a BukkitTask that's cancellable

    void stop(@Nullable  Component msg);


    boolean isStopped();

    BukkitTask getTask();
}
