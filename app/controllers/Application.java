package controllers;

import models.*;
import play.mvc.*;
import play.modules.guice.*;

@InjectSupport
public class Application extends Controller {
    public static void index() {
        render();
    }

    // download file dinh kem theo id
    public static void downloadFile(Long id) {
        final FileDinhKem fdk = FileDinhKem.findById(id);
        notFoundIfNull(fdk);
        response.setContentTypeIfNotSet(fdk.fileDinhKem.type());
        renderBinary(fdk.fileDinhKem.get(), fdk.fileName);
    }
}