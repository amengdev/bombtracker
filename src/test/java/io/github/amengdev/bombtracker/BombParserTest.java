package io.github.amengdev.bombtracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BombParserTest {
    // wynncraft icon glyphs
    static final String ICON = "\uDAFF\uDFFC\uDAFF\uDFFF\uDAFF\uDFFE ";
    static final String WRAP = "\uDAFF\uDFFC\uDB80\uDC06 ";

    static final String[] BOMB_TYPES = {
            "Loot Chest",
            "Combat Experience",
            "Profession Experience",
            "Profession Speed",
            "Loot"
    };

    @Test
    void parsesEveryBombTypeWrappedAtAnyWordBoundary() {
        for (String type : BOMB_TYPES) {
            String sentence = "Fridgethechill has thrown " + "a" + " " + type + " Bomb on NA26";

            for (int i = 0; i < sentence.length(); i++) {
                if (sentence.charAt(i) != ' ') continue;

                for (String lineBreak : new String[]{"\n" + WRAP, " \n" + WRAP}) {
                    String msg = ICON + sentence.substring(0, i) + lineBreak + sentence.substring(i + 1);
                    BombParser.Bomb b = BombParser.parse(msg);

                    String where = type + ", break at index " + i;
                    assertNotNull(b, where);
                    assertEquals("Fridgethechill", b.player(), where);
                    assertEquals(type, b.type(), where);
                    assertEquals("NA26", b.server(), where);
                }
            }
        }
    }
}