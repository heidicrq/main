package seedu.address.model.task;

import java.util.function.Predicate;

import seedu.address.model.module.ModuleCode;


/**
 * Tests that a {@code Task}'s {@code Description} matches any of the keywords given.
 */
public class TaskContainsInModulePredicate implements Predicate<Task> {
    private final String moduleCode;

    public TaskContainsInModulePredicate(String moduleCode) {
        assert (ModuleCode.isValidModuleCode(moduleCode)) : "Module code should be valid";
        this.moduleCode = moduleCode;
    }

    @Override
    public boolean test(Task task) {
        return task.getModuleCode().toString().equals(moduleCode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsInModulePredicate // instanceof handles nulls
                && moduleCode.equals(((TaskContainsInModulePredicate) other).moduleCode)); // state check
    }

    public String getModuleCode() {
        return moduleCode;
    }
}