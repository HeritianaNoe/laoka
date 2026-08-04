package com.lakabe.laoka.gs.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lakabe.laoka.gs.database.dao.CategoryDao;
import com.lakabe.laoka.gs.database.dao.FadyDao;
import com.lakabe.laoka.gs.database.dao.FoodDao;
import com.lakabe.laoka.gs.model.Category;
import com.lakabe.laoka.gs.model.Fady;
import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.utils.Converters;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class, Category.class, Fady.class},version = 1, exportSchema = false)
public abstract class LaokaDataBase extends RoomDatabase{
    private static Context context;
    private static LaokaDataBase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    public abstract CategoryDao categoryDao();
    public abstract FadyDao fadyDao();
    public abstract FoodDao foodDao();

    public static synchronized LaokaDataBase getInstance(Context c){
        context = c;
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),LaokaDataBase.class,"app_laoka_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbExecutor.execute(() -> {
                CategoryDao category = instance.categoryDao();
                category.insert(new Category("Henakisoa", getStringAsset(context, "images/category/henakisoa.png")));
                category.insert(new Category("Henomby", getStringAsset(context, "images/category/henomby.png")));
                category.insert(new Category("Vorona", getStringAsset(context, "images/category/vorona.png")));
                category.insert(new Category("Akoho", getStringAsset(context, "images/category/akoho.png")));
                category.insert(new Category("Hazandrano", getStringAsset(context, "images/category/hazandrano.png")));
                category.insert(new Category("Ondry", getStringAsset(context, "images/category/ondry.png")));
                category.insert(new Category("Paty", getStringAsset(context, "images/category/misao.png")));
                category.insert(new Category("Taovan-kena", getStringAsset(context, "images/category/taovan_kena.png")));
                category.insert(new Category("Sosisy", getStringAsset(context, "images/category/sosisy.png")));
                category.insert(new Category("Hafa", getStringAsset(context, "images/category/hafa.png")));

                FadyDao fady = instance.fadyDao();
                fady.insert(new Fady("Osy"));

                FoodDao food = instance.foodDao();
                food.insert(new Food("Henakisoa ritra","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henakisoa_ritra.png"), true, 5, 1));
                food.insert(new Food("Henakisoa ravitoto","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henakisoa_ravitoto.png"), true, 3, 1));
                food.insert(new Food("Henakisoa voanjobory","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henakisoa_voanjobory.png"), true, 2, 1));
                food.insert(new Food("Henakisoa haricot vert","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henakiso_haricotvert.png"), false, 1, 1));
                food.insert(new Food("Henakisoa sosy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henakisoa_sosy.png"), false, 1, 1));
                food.insert(new Food("Henakisoa nedasina","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henakisoa_nedasina.png"), false, 1, 1));
                food.insert(new Food("Henakisoa tsaramaso","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henakisoa_tsaramaso.png"), false, 1, 1));
                food.insert(new Food("Tongon-kisoa sosy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/tongon-kisoa.png"), true, 2, 1));

                food.insert(new Food("Henomby ritra","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henomby_ritra.png"), true, 5, 2));
                food.insert(new Food("Henomby ovy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henomby_ovy.png"), false, 1, 2));
                food.insert(new Food("Henomby voanjobory","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henomby_voanjobory.png"), true, 2, 2));
                food.insert(new Food("Henomby baolina","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/henabolina.png"), true, 4, 2));

                food.insert(new Food("Vorona ritra","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/vorona.png"), true, 4, 3));
                food.insert(new Food("Vorona","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/vorona.png"), true, 4, 3));
                food.insert(new Food("Ganagana natono","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/ganagana.png"), true, 5, 3));

                food.insert(new Food("Akoho natono","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/akoho_natono.png"), true, 3, 4));
                food.insert(new Food("Akoho mananasy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/akoho_mananasy.png"), true, 5, 4));

                food.insert(new Food("Crevette sosy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/crevette.png"), true, 3, 5));
                food.insert(new Food("Crabe sosy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/crabe.png"), true, 3, 5));
                food.insert(new Food("Trondro sosy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/trondro_sosy.png"), true, 3, 5));

                food.insert(new Food("Ondry ritra","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/ondry_ritra.png"), true, 5, 6));

                food.insert(new Food("Misao","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/misao.png"), true, 3, 7));

                food.insert(new Food("Vorivorikena ritra","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/tripe_ritra.png"), false, 3, 8));
                food.insert(new Food("Vorivorikena petit pois","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/tripe_petit_pois.png"), false, 2, 8));
                food.insert(new Food("Vorivorikena sosy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/tripe_sosy.png"), true, 3, 8));
                food.insert(new Food("Vorivorikena tsaramaso","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/tripe_tsaramaso.png"), true, 3, 8));
                food.insert(new Food("Atikena sosy carry","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/atikena_carry.png"), false, 3, 8));
                food.insert(new Food("Atikena nedasina","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/atikena_nedasina.png"), true, 3, 8));
                food.insert(new Food("Atikena sosy","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/atikena_sosy.png"), true, 4, 8));

                food.insert(new Food("Sosisy kabaro","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/sosisy_nedasina.png"), true, 2, 9));
                food.insert(new Food("Sosisy nedasina","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/sosisy_kabaro.png"), true, 3, 9));
                food.insert(new Food("Sosisy tsaramaso","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/sosisy_tsaramaso.png"), true, 3, 9));
                food.insert(new Food("Sosisy voanjobory","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/sosisy_voanjobory.png"), false, 1, 9));

                food.insert(new Food("Riz cantonne","Zavatra ilaina menaka, sira", getStringAsset(context,"images/food/riz_cantonne.png"), true, 2, 10));
                food.insert(new Food("Ovy nedasina","Zavatra ilaina menaka, sira, lafarinina", getStringAsset(context,"images/food/frity.png"), true, 2, 10));
            });
        }
    };

    private static String getStringAsset(Context context, String strName){
        return Converters.fromBitmapToBase64(getBitmapFromAsset(context, strName));
    }

    private static Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(strName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            return null;
        }
        return bitmap;
    }
}
