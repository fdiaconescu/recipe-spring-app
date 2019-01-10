package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.RecipeCommand;
import fd.spring5.recipes.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;
    private final CategoryToCategoryCommand categoryConverter;

    public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter, CategoryToCategoryCommand categoryConverter) {
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null){
            return null;
        }

        final RecipeCommand output = new RecipeCommand();
        output.setCookTime(source.getCookTime());
        output.setDescription(source.getDescription());
        output.setDifficulty(source.getDifficulty());
        output.setDirections(source.getDirections());
        output.setId(source.getId());
        output.setNotes(notesConverter.convert(source.getNotes()));
        output.setPrepTime(source.getPrepTime());
        output.setCookTime(source.getCookTime());
        output.setPrepTime(source.getPrepTime());
        output.setServings(source.getServings());
        output.setSource(source.getSource());
        output.setUrl(source.getUrl());

        if(source.getCategories() != null && !source.getCategories().isEmpty()){
            source.getCategories().forEach(category ->
                    output.getCategories().add(categoryConverter.convert(category)));
        }

        if(source.getIngredients() != null && !source.getIngredients().isEmpty()){
            source.getIngredients().forEach(ingredient ->
                    output.getIngredients().add(ingredientConverter.convert(ingredient)));
        }
        return output;

    }
}
