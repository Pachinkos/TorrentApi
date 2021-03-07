package pro.solidexception;

import io.ebean.DB;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.server.types.files.SystemFile;
import pro.solidexception.models.Torrent;
import pro.solidexception.models.query.QTorrent;
import pro.solidexception.schemas.MetaTorrent;
import pro.solidexception.schemas.query.QMetaTorrent;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller("/v1/torrents")
public class TorrentApiController {

    public TorrentApiController() {

    }

    @Get("/search/{name}")
    List<MetaTorrent> getSearchTorrent(String name, @QueryValue @Nullable String quality) {
        StringBuilder query = new StringBuilder("select id, name, category, year, quality, size from torrent ");
        query.append("where name ilike :name ");
        if(quality != null){
            query.append("and quality = :quality ");
        }
        query.append("limit 20 ");

        return DB.findNative(MetaTorrent.class, query.toString())
                .setParameter("quality", quality)
                .setParameter("name", "%"+name+"%")
                .findList();
    }

    @Get("/pages/{page}")
    List<MetaTorrent> getTorrentsPage(int page, @QueryValue @Nullable String quality, @QueryValue(defaultValue = "50") int pageSize) {
        if(page <= 0) return null;
        pageSize = pageSize > 100 ? 100 : pageSize;
        pageSize = pageSize <= 0 ? 0 : pageSize;

        StringBuilder query = new StringBuilder("select id, name, category, year, quality, size from torrent ");
        if(quality != null){
            query.append("where quality = :quality ");
        }
        query.append("limit ").append(pageSize).append(" ").append("offset ").append((page - 1) * pageSize);

        return DB.findNative(MetaTorrent.class, query.toString())
                .setParameter("quality", quality)
                .findList();
    }

    @Get(value = "/images/{torrentId}")
    SystemFile getImage(int torrentId) {
        try {
            Torrent torrent = DB.findNative(Torrent.class, "select image from torrent where id = :id")
                    .setParameter("id", torrentId)
                    .findOne();

            if(torrent == null || torrent.getImage() == null || torrent.getImage().length == 0) return null;

            File file = File.createTempFile(torrentId+"", ".png");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(torrent.getImage());
            fos.close();
            return new SystemFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Get(value = "/files/{torrentId}")
    SystemFile getTorrent(int torrentId) {
        try {
            Torrent torrent = DB.findNative(Torrent.class, "select torrent from torrent where id = :id")
                    .setParameter("id", torrentId)
                    .findOne();

            if(torrent == null || torrent.getTorrent() == null || torrent.getTorrent().length == 0) return null;

            File file = File.createTempFile(torrentId+"", ".torrent");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(torrent.getTorrent());
            fos.close();
            return new SystemFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}