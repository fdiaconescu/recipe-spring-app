package fd.spring5.recipes.services;

import fd.spring5.recipes.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    public Set<UnitOfMeasureCommand> listAllUoms();
}
