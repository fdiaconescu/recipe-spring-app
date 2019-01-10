package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.IngredientCommand;
import fd.spring5.recipes.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient output = new Ingredient();
        output.setId(source.getId());
        output.setAmount(source.getAmount());
        output.setDescription(source.getDescription());
        output.setUom(uomConverter.convert(source.getUom()));

        return output;
    }
}
