package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.CategoryCommand;
import fd.spring5.recipes.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void convert() {
        //given
        Long id = 1L;
        String description = "description";

        Category category = new Category();
        category.setId(id);
        category.setDescription(description);


        //when
        CategoryCommand commandObj = converter.convert(category);

        //then
        assertEquals(id, commandObj.getId());
        assertEquals(description, commandObj.getDescription());
    }
}