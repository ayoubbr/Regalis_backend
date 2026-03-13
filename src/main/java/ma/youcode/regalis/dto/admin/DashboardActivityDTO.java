package ma.youcode.regalis.dto.admin;

public record DashboardActivityDTO(
    String type,
    String user,
    String message,
    String time,
    String icon,
    boolean critical
) {}
