package net.miraclepvp.kitpvp.data.user;

import org.bukkit.Material;

import java.util.Random;

public class Abilities {

    public static Boolean chance(Integer percentage) {
        if(percentage == 100) return true;
        Integer v = new Random().nextInt(100) + 1;
        if (v >= percentage)
            return false;
        return true;
    }

    public enum AbilityType {

        GOLDEN_APPLE(
                Material.GOLDEN_APPLE,
                "Golden Apple after kill",
                "Have a chance of getting a golden apple after you make a kill.",
                600,
                400,
                700,
                "percentage chance",
                50,
                60,
                70,
                80,
                90,
                100
        ),
        STRENGTH(
                Material.DIAMOND_SWORD,
                "Strength after kill",
                "Get a few seconds of strength after kill.",
                550,
                500,
                800,
                "seconds",
                2,
                3,
                4,
                5,
                6,
                8

        ),
        ARROW_BACK(
                Material.ARROW,
                "Arrow back after hit",
                "Have a chance of getting your arrow back after hitting someone with it.",
                400,
                300,
                500,
                "percentage chance",
                10,
                20,
                30,
                40,
                50,
                60
        ),
        ABSORTION(
                Material.GOLD_CHESTPLATE,
                "Absortion at spawn",
                "Get a few seconds of absortion after spawning.",
                400,
                300,
                500,
                "seconds",
                4,
                5,
                6,
                7,
                8,
                10
        ),
        REGENERATION(
                Material.GHAST_TEAR,
                "Regeneration after kill",
                "Get a few seconds of regeneration after kill.",
                600,
                400,
                700,
                "seconds",
                2,
                3,
                5,
                6,
                8,
                10
        ),
        MORE_COINS(
                Material.DOUBLE_PLANT,
                "More coins at kill",
                "Have chance to get 5 coins extra after you make a kill.",
                500,
                500,
                600,
                "percentage chance",
                5,
                10,
                15,
                20,
                25,
                30
        ),
        MORE_EXP(
                Material.EXP_BOTTLE,
                "More exp at kill",
                "Have chance to get 5 experience extra after you make a kill.",
                500,
                500,
                600,
                "percentage chance",
                5,
                10,
                15,
                20,
                25,
                30
        ),
        ENDERPEARL(
                Material.ENDER_PEARL,
                "Enderpearl after kill",
                "Have chance to get a enderpearl after you make a kill.",
                550,
                500,
                800,
                "percentage chance",
                3,
                6,
                9,
                12,
                15,
                20
        ),
        REGEN_SPAWN(
                Material.BEACON,
                "Regeneration at spawn",
                "Get a few seconds of regeneration after spawning.",
                350,
                275,
                350,
                "seconds",
                2,
                3,
                4,
                5,
                6,
                8
        );

        Material
                icon;
        String
                name,
                description,
                action;
        Integer
                first,
                upgrade,
                miracle,
                lvl1,
                lvl2,
                lvl3,
                lvl4,
                lvl5,
                lvl6;

        AbilityType(Material icon, String name, String description, Integer first, Integer upgrade, Integer miracle, String action, Integer lvl1, Integer lvl2, Integer lvl3, Integer lvl4, Integer lvl5, Integer lvl6) {
            this.icon = icon;
            this.name = name;
            this.description = description;
            this.first = first;
            this.upgrade = upgrade;
            this.miracle = miracle;
            this.action = action;
            this.lvl1 = lvl1;
            this.lvl2 = lvl2;
            this.lvl3 = lvl3;
            this.lvl4 = lvl4;
            this.lvl5 = lvl5;
            this.lvl6 = lvl6;
        }

        public Integer getPrice(Integer level) {
            if (level == 1)
                return first;
            else if (level == 6)
                return miracle;
            else
                return upgrade;
        }

        public String getName() {
            return name;
        }

        public Material getIcon() {
            return icon;
        }

        public String getDescription() {
            return description;
        }

        public String getAction() {
            return action;
        }

        public Integer getLvl(Integer level) {
            switch (level) {
                case 1:
                    return getLvl1();
                case 2:
                    return getLvl2();
                case 3:
                    return getLvl3();
                case 4:
                    return getLvl4();
                case 5:
                    return getLvl5();
                case 6:
                    return getLvl6();
                default:
                    return 0;
            }
        }

        public Integer getLvl1() {
            return lvl1;
        }

        public Integer getLvl2() {
            return lvl2;
        }

        public Integer getLvl3() {
            return lvl3;
        }

        public Integer getLvl4() {
            return lvl4;
        }

        public Integer getLvl5() {
            return lvl5;
        }

        public Integer getLvl6() {
            return lvl6;
        }
    }

    public static class Ability {

        private String type;
        private Integer level;

        public Ability(String type, Integer level) {
            this.type = type;
            this.level = level;
        }

        public AbilityType getType() {
            return AbilityType.valueOf(type);
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Integer getPrice() {
            Integer level = this.level + 1;
            if (level == 1)
                return getType().first;
            else if (level == 6)
                return getType().miracle;
            else
                return getType().upgrade;
        }
    }
}
