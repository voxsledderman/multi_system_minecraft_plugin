package com.xdd.serverPlugin.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class TextUtils {

    public static TextComponent formatMessage(String content, TextDecoration decoration, TextColor color){
        return Component.text(content).decoration(decoration,false).color(color);
    }
    public static TextComponent formatMessage(String content,TextColor color){
        return Component.text(content).color(color);
    }
}
