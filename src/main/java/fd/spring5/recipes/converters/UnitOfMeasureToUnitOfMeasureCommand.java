package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.UnitOfMeasureCommand;
import fd.spring5.recipes.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {

        if(source == null){
            return null;
        }

        final UnitOfMeasureCommand output = new UnitOfMeasureCommand();
        output.setId(source.getId());
        output.setDescription(source.getDescription());

        return output;
    }
}
