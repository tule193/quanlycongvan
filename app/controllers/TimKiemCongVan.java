package controllers;

import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import models.User;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.action.search.SearchRequestBuilder;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import dao.UserDao;

import play.Logger;
import play.i18n.Messages;
import play.modules.elasticsearch.ElasticSearchPlugin;
import play.modules.guice.InjectSupport;
import play.mvc.With;
import utils.Constants;
import utils.FeedBack;

@InjectSupport
@With(Secure.class)
public class TimKiemCongVan extends AbstractController{
	
	@Inject
	static UserDao userDao;
	
	public static void TimKiem(int filter, String searchText) {
		// If null ==> render()
		if (searchText == null || searchText.equalsIgnoreCase("")) {
			render(filter);
		}

		// Keep original for view, search with lower case text
		String viewText = searchText;		
		searchText = searchText.toLowerCase();		
		
		String username = session.get("username");
		FeedBack<User> result = userDao.findByUsername(username);
		if (!result.isSuccess) {
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("Error_UserNotFound!"));
			QuanLyCongVanDen.danhSachCongVanDen();
		}
		
		User user = result.data;		
		
		Client client = ElasticSearchPlugin.client();
		
		String[] searchs = searchText.split(" ");
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		for (String search : searchs) {
			if (search.length() >= 2) {
				boolQuery.should(QueryBuilders.wildcardQuery("tieuDe", "*" + search + "*"));
				boolQuery.should(QueryBuilders.wildcardQuery("trichYeu", "*" + search + "*"));
				boolQuery.should(QueryBuilders.wildcardQuery("noidung", "*" + search + "*"));
			}
		}
		
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch("test")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(boolQuery)
		        .addSort("_score", SortOrder.DESC)
		        .addSort("id", SortOrder.DESC)
		        .addHighlightedField("tieuDe")
		        .addHighlightedField("trichYeu")
		        .addHighlightedField("noidung")
		        .setTrackScores(true)
		        .setFrom(0).setSize(60).setExplain(true);	
		
		// Filter according to role : Allow to view all on VAN_THU and BAN_GIAM_HIEU
		if (!(user.phanQuyen == User.ROLE.VAN_THU) && !(user.phanQuyen == User.ROLE.BAN_GIAM_HIEU)) {
			// Filter according to search choice		
			if (filter != 0) {
				searchRequestBuilder.setFilter(
						FilterBuilders.andFilter(
								FilterBuilders.termFilter("typeCongVan", filter - 1),
								FilterBuilders.termFilter("nguoiLienQuan", user.username)
								)
						);
			}
			else {
				searchRequestBuilder.setFilter(FilterBuilders.termFilter("nguoiLienQuan", user.username));
			}			
		}
		else {
			if (filter != 0) {
				searchRequestBuilder.setFilter(FilterBuilders.termFilter("typeCongVan", filter - 1));
			}
		}
		
		// Execute search
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		
		SearchHit[] hits = searchResponse.hits().getHits();
		int i = 0;
		ArrayList<KetQuaTimKiem> ketquas = new ArrayList<KetQuaTimKiem>();
		
		while (i < hits.length) {
			String id = hits[i].getSource().get("id").toString();
			String tieuDe = hits[i].getSource().get("tieuDe") == null ? "" : hits[i].getSource().get("tieuDe").toString();			
			int typeCongVan = hits[i].getSource().get("typeCongVan") == null ? KetQuaTimKiem.TYPE_KET_QUA.LICH :
					Integer.valueOf(hits[i].getSource().get("typeCongVan").toString());
			String hightlight = "";
			if (hits[i].getHighlightFields().get("tieuDe") != null) {
				hightlight = hits[i].getHighlightFields().get("tieuDe").fragments()[0];
			} else if (hits[i].getHighlightFields().get("trichYeu") != null) {
				hightlight = hits[i].getHighlightFields().get("trichYeu").fragments()[0];
			} else {
				hightlight = hits[i].getHighlightFields().get("noidung").fragments()[0];
			}
			
			ketquas.add(
					new KetQuaTimKiem(id, tieuDe, hightlight, typeCongVan)					
					);
			i++;
		}
		
		// Revert to original text for view
		searchText = viewText;
		render(filter, searchText, ketquas);
	}	

}


