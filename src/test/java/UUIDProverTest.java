import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.schar.whitelister.whitelist.UUIDProvider;

import java.util.List;

public class UUIDProverTest {

    private static final List<String> correctPlayerNames = List.of(
            "Synthesyzer",
            "_Tony_",
            "I_Always_PvP",
            "Riboto"
    );
    private static final List<String> incorrectPlayerNames = List.of(
            "pwqruhtgaps;orfiudgh",
            "vsdfgsrg",
            "eeeeecccwfac",
            "bruhbruhbruhbruhokbruh"
    );

    @Test
    public void testValidPlayerNames() {
        for (String playerName : correctPlayerNames) {
            assertNameIsValid(playerName);
        }
    }

    @Test
    public void testInvalidPlayerNames() {
        for (String playerName : incorrectPlayerNames) {
            assertNameIsInvalid(playerName);
        }
    }

    public void assertNameIsValid(String playerName) {
        var playerJSON = UUIDProvider.getPlayerJSON(playerName);

        Assertions.assertTrue(playerJSON.isPresent());

        var playerNameResponse = playerJSON.get().get("name");
        Assertions.assertEquals(playerNameResponse, playerName);
    }

    public void assertNameIsInvalid(String playerName) {
        var playerJSON = UUIDProvider.getPlayerJSON(playerName);
        Assertions.assertTrue(playerJSON.isEmpty());
    }
}
