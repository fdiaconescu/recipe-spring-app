package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.NotesCommand;
import fd.spring5.recipes.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if(source == null) {
            return null;
        }

        final Notes output = new Notes();
        output.setId(source.getId());
        output.setRecipeNotes(source.getRecipeNotes());

        return output;
    }
}
