package com.suvidha.bazaaratyourdwaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ViewItem extends AppCompatActivity {

    ViewPager viewPager,viewPager_DR;
    Context context = this;
    TabLayout tabLayout_DR;
    List<String> images;
    RecyclerView recyclerView_products_show;
    TextView textView_heading,textView_item_name,textView_price,textView_discount;
    ImageView imageView_share,imageView_add_fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recyclerView_products_show = findViewById(R.id.view_item_rv_like_products);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_products_show.setLayoutManager(linearLayoutManager);
        recyclerView_products_show.setAdapter(new RVAdapter_products_show(context));
        textView_discount = findViewById(R.id.view_item_tv_discount);
        textView_heading = findViewById(R.id.view_item_tv_item_heading);
        textView_item_name = findViewById(R.id.view_item_tv_item_name);
        textView_price = findViewById(R.id.view_item_tv_price);
        imageView_share = findViewById(R.id.view_item_iv_share);
        imageView_add_fav = findViewById(R.id.view_item_iv_add_fav);

        images = new ArrayList<>();

        tabLayout_DR = findViewById(R.id.view_item_tabLayout);
        tabLayout_DR.addTab(tabLayout_DR.newTab().setText("Description"));
        tabLayout_DR.addTab(tabLayout_DR.newTab().setText("Reviews"));
        tabLayout_DR.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter_ItemsDesc viewPagerAdapter_itemsDesc = new ViewPagerAdapter_ItemsDesc(getSupportFragmentManager(),tabLayout_DR.getTabCount());

        viewPager_DR = findViewById(R.id.view_item_vp_DR);
        viewPager_DR.setAdapter(viewPagerAdapter_itemsDesc);
        viewPager_DR.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_DR));
        tabLayout_DR.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_DR.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fetchData(getIntent().getStringExtra("itemId"));
    }

    private void fetchData(String id)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://bazaaratyourdwaar.000webhostapp.com/fetchitems.php?id=" + id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Product product = new Product();
                        try {
                            JSONArray jsonArray = response.getJSONArray(0);
                            product.setCategory(jsonArray.get(0).toString());
                            product.setSubcategory(jsonArray.get(1).toString());
                            product.setName(jsonArray.get(2).toString());
                            product.setCurrent_price(jsonArray.get(3).toString());
                            product.setRaw_price(jsonArray.get(4).toString());
                            product.setCurrency(jsonArray.get(5).toString());
                            product.setDiscount(jsonArray.get(6).toString());
                            product.setLikes_count(jsonArray.get(7).toString());
                            product.setIs_new(jsonArray.get(8).toString());
                            product.setBrand(jsonArray.get(9).toString());
                            product.setBrand_url(jsonArray.get(10).toString());
                            product.setCodcountry(jsonArray.get(11).toString());
                            product.setVariation_0_color(jsonArray.get(12).toString());
                            product.setVariation_1_color(jsonArray.get(13).toString());
                            product.setVariation_0_thumbnail(jsonArray.get(14).toString());
                            product.setVariation_0_image(jsonArray.get(15).toString());
                            product.setVariation_1_thumbnail(jsonArray.get(16).toString());
                            product.setVariation_1_image(jsonArray.get(17).toString());
                            product.setImage_url(jsonArray.get(18).toString());
                            product.setUrl(jsonArray.get(19).toString());
                            product.setId(jsonArray.get(20).toString());
                            product.setModel(jsonArray.get(21).toString());

                            textView_price.setText(product.getCurrent_price());
                            textView_discount.setText(product.getDiscount());
                            textView_heading.setText(product.getName());
                            textView_item_name.setText(product.getName());
                            viewPager = findViewById(R.id.view_item_vp_product_images);
                            images.add(product.getImage_url());
                            if(!product.getVariation_0_image().isEmpty())
                            images.add(product.getVariation_0_image());
                            if(!product.getVariation_1_image().isEmpty())
                            images.add(product.getVariation_1_image());
                            ViewPagerAdapterProducts viewPagerAdapterProducts = new ViewPagerAdapterProducts(context,images);
                            viewPager.setAdapter(viewPagerAdapterProducts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public class Product
    {
        private String category,subcategory,name,current_price,raw_price,currency,discount,likes_count,is_new,brand,brand_url,codcountry,variation_0_color,variation_1_color,variation_0_thumbnail,variation_0_image,variation_1_thumbnail,variation_1_image,image_url,url,id,model;

        public Product()
        {

        }

        public Product(String category, String subcategory, String name, String current_price, String raw_price, String currency, String discount, String likes_count, String is_new, String brand, String brand_url, String codcountry, String variation_0_color, String variation_1_color, String variation_0_thumbnail, String variation_0_image, String variation_1_thumbnail, String variation_1_image, String image_url, String url, String id, String model) {
            this.category = category;
            this.subcategory = subcategory;
            this.name = name;
            this.current_price = current_price;
            this.raw_price = raw_price;
            this.currency = currency;
            this.discount = discount;
            this.likes_count = likes_count;
            this.is_new = is_new;
            this.brand = brand;
            this.brand_url = brand_url;
            this.codcountry = codcountry;
            this.variation_0_color = variation_0_color;
            this.variation_1_color = variation_1_color;
            this.variation_0_thumbnail = variation_0_thumbnail;
            this.variation_0_image = variation_0_image;
            this.variation_1_thumbnail = variation_1_thumbnail;
            this.variation_1_image = variation_1_image;
            this.image_url = image_url;
            this.url = url;
            this.id = id;
            this.model = model;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCurrent_price() {
            return current_price;
        }

        public void setCurrent_price(String current_price) {
            this.current_price = current_price;
        }

        public String getRaw_price() {
            return raw_price;
        }

        public void setRaw_price(String raw_price) {
            this.raw_price = raw_price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getLikes_count() {
            return likes_count;
        }

        public void setLikes_count(String likes_count) {
            this.likes_count = likes_count;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBrand_url() {
            return brand_url;
        }

        public void setBrand_url(String brand_url) {
            this.brand_url = brand_url;
        }

        public String getCodcountry() {
            return codcountry;
        }

        public void setCodcountry(String codcountry) {
            this.codcountry = codcountry;
        }

        public String getVariation_0_color() {
            return variation_0_color;
        }

        public void setVariation_0_color(String variation_0_color) {
            this.variation_0_color = variation_0_color;
        }

        public String getVariation_1_color() {
            return variation_1_color;
        }

        public void setVariation_1_color(String variation_1_color) {
            this.variation_1_color = variation_1_color;
        }

        public String getVariation_0_thumbnail() {
            return variation_0_thumbnail;
        }

        public void setVariation_0_thumbnail(String variation_0_thumbnail) {
            this.variation_0_thumbnail = variation_0_thumbnail;
        }

        public String getVariation_0_image() {
            return variation_0_image;
        }

        public void setVariation_0_image(String variation_0_image) {
            this.variation_0_image = variation_0_image;
        }

        public String getVariation_1_thumbnail() {
            return variation_1_thumbnail;
        }

        public void setVariation_1_thumbnail(String variation_1_thumbnail) {
            this.variation_1_thumbnail = variation_1_thumbnail;
        }

        public String getVariation_1_image() {
            return variation_1_image;
        }

        public void setVariation_1_image(String variation_1_image) {
            this.variation_1_image = variation_1_image;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }
}