package dev.kayange.projectmanagementsystem.constants;

public class AppConstants {
    public static final String[] PUBLIC_LINKS = {
            "/auth/**",
            "/v*/api-docs",
            "/v*/api-docs/**",
            "/swagger-resources/",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
    };

    public static final String ZERO = "0";
    public static final String MAX_DAYS = "365";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final int MAX_PAGE_SIZE = 10000;
    public static final String FILE_NOT_EXIST = "FILE_NOT_EXIST";
    public static final String UPLOADS_DIRECTORY = "/var/lib/server/data";
    public static final String EXCEL_FILE_PATH = "/var/lib/server/data/upload.xlsx";
    public static final String APP_URL = "";
    public static final String NEXMO_API_KEY = "";
    public static final String NEXMO_API_SECRET = "";
}
