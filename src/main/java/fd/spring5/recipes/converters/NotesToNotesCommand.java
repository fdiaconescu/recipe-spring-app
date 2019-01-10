package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.NotesCommand;
import fd.spring5.recipes.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if(source == null) {
            return null;
        }

        final NotesCommand output = new NotesCommand();
        output.setId(source.getId());
        output.setRecipeNotes(source.getRecipeNotes());

        return output;
    }
}
