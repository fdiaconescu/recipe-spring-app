package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.RecipeCommand;
import fd.spring5.recipes.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;
    private final CategoryCommandToCategory categoryConverter;

    public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter, CategoryCommandToCategory categoryConverter) {
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null){
            return null;
        }

        final Recipe output = new Recipe();
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
        output.setImage(source.getImage());

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
