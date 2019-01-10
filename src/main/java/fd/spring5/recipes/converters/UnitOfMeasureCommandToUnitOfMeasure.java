package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.UnitOfMeasureCommand;
import fd.spring5.recipes.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if(source == null){
            return null;
        }

        final UnitOfMeasure output = new UnitOfMeasure();
        output.setId(source.getId());
        output.setDescription(source.getDescription());

        return output;
    }
}
