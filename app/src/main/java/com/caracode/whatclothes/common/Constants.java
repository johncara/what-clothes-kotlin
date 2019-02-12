package com.caracode.whatclothes.common;

import java.util.Arrays;
import java.util.List;

public interface Constants {

    double MIN_TEMP = -100;
    double MAX_TEMP = 100;
    String DATE_FORMAT = "EEE d MMM";
    String FLICKER_PHOTO_URL_FORMAT = "https://farm%1$s.staticflickr.com/%2$s/%3$s_%4$s.jpg";
    List<String> BACKUP_PHOTOS = Arrays.asList(
            "http://images.china.cn/attachement/jpg/site1007/20130912/001422373e19139b59f101.jpg",
            "http://www.wallpapersworldonline.com/wp-content/uploads/2014/01/beautiful-night-scenery-123.jpg",
            "http://travelhdwallpapers.com/wp-content/uploads/2017/01/london-tower-bridge-wallpaper.jpg",
            "http://bestworldtourism.com/wp-content/uploads/2011/07/Energy-London-Eye-Capsule.jpg",
            "https://ak2.picdn.net/shutterstock/videos/8425261/thumb/1.jpg",
            "https://c8.alamy.com/comp/B5J3W1/st-jamess-park-with-big-ben-in-background-london-B5J3W1.jpg");
}
