package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.IngredientCommand;
import fd.spring5.recipes.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        final IngredientCommand output = new IngredientCommand();
        output.setId(source.getId());
        output.setAmount(source.getAmount());
        output.setDescription(source.getDescription());
        output.setUom(uomConverter.convert(source.getUom()));

        if(source.getRecipe() != null){
            output.setRecipeId(source.getRecipe().getId());
        }

        return output;
    }
}
