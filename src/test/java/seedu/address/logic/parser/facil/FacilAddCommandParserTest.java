package seedu.address.logic.parser.facil;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OFFICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.OFFICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.OFFICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFacilitators.AMY;
import static seedu.address.testutil.TypicalFacilitators.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.facil.FacilAddCommand;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.FacilitatorBuilder;

public class FacilAddCommandParserTest {
    private FacilAddCommandParser parser = new FacilAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Facilitator expectedFacilitator = new FacilitatorBuilder(BOB).withModuleCodes(VALID_MODULE_CODE_CS2101).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + OFFICE_DESC_BOB + MODULE_CODE_DESC_CS2101, new FacilAddCommand(expectedFacilitator));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + OFFICE_DESC_BOB + MODULE_CODE_DESC_CS2101, new FacilAddCommand(expectedFacilitator));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + OFFICE_DESC_BOB + MODULE_CODE_DESC_CS2101, new FacilAddCommand(expectedFacilitator));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + OFFICE_DESC_BOB + MODULE_CODE_DESC_CS2101, new FacilAddCommand(expectedFacilitator));

        // multiple offices - last office accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + OFFICE_DESC_AMY
                + OFFICE_DESC_BOB + MODULE_CODE_DESC_CS2101, new FacilAddCommand(expectedFacilitator));

        // multiple module codes - all accepted
        Facilitator expectedFacilitatorMultipleModuleCodes = new FacilitatorBuilder(BOB)
                .withModuleCodes(VALID_MODULE_CODE_CS2101, VALID_MODULE_CODE_CS2103T).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + OFFICE_DESC_BOB
                        + MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_CS2101,
                new FacilAddCommand(expectedFacilitatorMultipleModuleCodes));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // missing phone
        Facilitator expectedFacilitator = new FacilitatorBuilder(AMY).withPhone(null).build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + OFFICE_DESC_AMY + MODULE_CODE_DESC_CS2101,
                new FacilAddCommand(expectedFacilitator));

        // missing email
        expectedFacilitator = new FacilitatorBuilder(AMY).withEmail(null).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + OFFICE_DESC_AMY + MODULE_CODE_DESC_CS2101,
                new FacilAddCommand(expectedFacilitator));

        // missing office
        expectedFacilitator = new FacilitatorBuilder(AMY).withOffice(null).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + MODULE_CODE_DESC_CS2101,
                new FacilAddCommand(expectedFacilitator));

        // multiple optional fields missing
        expectedFacilitator = new FacilitatorBuilder(AMY).withPhone(null).withOffice(null).build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + MODULE_CODE_DESC_CS2101,
                new FacilAddCommand(expectedFacilitator));

        // zero module codes
        expectedFacilitator = new FacilitatorBuilder(AMY).withModuleCodes().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + OFFICE_DESC_AMY,
                new FacilAddCommand(expectedFacilitator));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacilAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + OFFICE_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_OFFICE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_allOptionalFieldsMissing_failure() {
        String expectedMessage = FacilAddCommand.MESSAGE_NOT_ADDED;

        // only name provided
        assertParseFailure(parser, NAME_DESC_BOB, expectedMessage);

        // all optional prefixes missing
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_OFFICE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + OFFICE_DESC_BOB
                + MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_CS2101, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + OFFICE_DESC_BOB
                + MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_CS2101, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + OFFICE_DESC_BOB
                + MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_CS2101, Email.MESSAGE_CONSTRAINTS);

        // invalid office
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_OFFICE_DESC
                + MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_CS2101, Office.MESSAGE_CONSTRAINTS);

        // invalid module code
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + OFFICE_DESC_BOB
                + INVALID_MODULE_CODE_DESC + VALID_MODULE_CODE_CS2101, ModuleCode.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_OFFICE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + OFFICE_DESC_BOB + MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_CS2101,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacilAddCommand.MESSAGE_USAGE));
    }
}
