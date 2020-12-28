package library.maxwell.module.slideshow.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlideShowDto {
	private Integer slideShowId;
	private String title;
	private String subTitle;
	private String img;
	private Boolean statusShow = true;
	private LocalDateTime createdAt;
	private Boolean status = true;
	private Integer userId;

}
