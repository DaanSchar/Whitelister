import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.schar.whitelister.whitelist.WhiteList;

import java.util.List;

public class WhiteListTester {

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
    public void testIncorrectNames() {
        WhiteList whiteList = new WhiteList(incorrectPlayerNames);
        Assertions.assertEquals(0, whiteList.getAddedPlayers().size());
        Assertions.assertEquals(incorrectPlayerNames.size(), whiteList.getFailedPlayers().size());
    }

    @Test
    public void testCorrectNames() {
        WhiteList whiteList = new WhiteList(correctPlayerNames);
        Assertions.assertEquals(correctPlayerNames.size(), whiteList.getAddedPlayers().size());
        Assertions.assertEquals(0, whiteList.getFailedPlayers().size());
    }

}
