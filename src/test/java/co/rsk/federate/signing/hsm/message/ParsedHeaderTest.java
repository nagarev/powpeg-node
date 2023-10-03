package co.rsk.federate.signing.hsm.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.ethereum.config.blockchain.upgrades.ActivationConfig;
import org.ethereum.core.BlockHeader;
import org.ethereum.core.BlockHeaderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spongycastle.util.encoders.Hex;

/**
 * Created by Kelvin Isievwore on 01/08/2023.
 */
public class ParsedHeaderTest {

    private BlockHeader blockHeader;
    private List<BlockHeader> brothers;
    private ParsedHeader parsedHeader;

    @BeforeEach
    void setup() {
        BlockHeaderBuilder blockHeaderBuilder = new BlockHeaderBuilder(mock(ActivationConfig.class));
        blockHeader = blockHeaderBuilder.setNumber(1).build();
        brothers = Arrays.asList(
            blockHeaderBuilder.setNumber(101).build(),
            blockHeaderBuilder.setNumber(102).build()
        );
        parsedHeader = new ParsedHeader(blockHeader, brothers);
    }

    @Test
    void getBlockHeader() {
        String serializedHeader = Hex.toHexString(blockHeader.getFullEncoded());
        assertEquals(serializedHeader, parsedHeader.getBlockHeader());
    }

    @Test
    void getBrothers() {
        String[] actualBrothers = parsedHeader.getBrothers();
        assertEquals(2, actualBrothers.length);
        for (int i = 0; i < actualBrothers.length; i++) {
            assertEquals(Hex.toHexString(brothers.get(i).getFullEncoded()), actualBrothers[i]);
        }
    }

    @Test
    void getBrothers_empty_brothers() {
        parsedHeader = new ParsedHeader(blockHeader, Collections.emptyList());

        String[] actualBrothers = parsedHeader.getBrothers();

        assertEquals(0, actualBrothers.length);
        for (int i = 0; i < actualBrothers.length; i++) {
            assertEquals(Hex.toHexString(brothers.get(i).getFullEncoded()), actualBrothers[i]);
        }
    }
}
