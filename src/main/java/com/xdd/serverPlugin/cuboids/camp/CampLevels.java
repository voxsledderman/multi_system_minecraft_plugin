package com.xdd.serverPlugin.cuboids.camp;

import com.xdd.serverPlugin.npc.campNpc.MayorNPC;
import lombok.Getter;
import java.util.List;

@Getter
public enum CampLevels {

    LEVEL1(15, List.of(MayorNPC.npcKey)),
    LEVEL2(30, List.of(MayorNPC.npcKey)),
    LEVEL3(50, List.of(MayorNPC.npcKey)),
    LEVEL4(75, List.of(MayorNPC.npcKey)),
    LEVEL5(100, List.of(MayorNPC.npcKey)),
    LEVEL6(130, List.of(MayorNPC.npcKey)),
    LEVEL7(160, List.of(MayorNPC.npcKey)),
    LEVEL8(200, List.of(MayorNPC.npcKey)),
    LEVEL9(225, List.of(MayorNPC.npcKey)),
    LEVEL10(250, List.of(MayorNPC.npcKey));

    private final int borderSize;
    private final List<String> unlockedNpcs;

    CampLevels(int borderSize, List<String> unlockedNpcs) {
        this.borderSize = borderSize;
        this.unlockedNpcs = unlockedNpcs;
    }

    public static CampLevels getEnumFromInteger(int lvl){
        for(CampLevels level : CampLevels.values()){
            if(level.ordinal() + 1 == lvl) return level;
        }
        return null;
    }
}
