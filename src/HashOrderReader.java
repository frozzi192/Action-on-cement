import com.sun.source.tree.CompoundAssignmentTree;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HashOrderReader extends AbstractOrderParser{


    @Override
    protected String getDelimiter() {
        return "#";
    }


}
       