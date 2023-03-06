package me.ogq.ocp.sample.gateway.elasticsearch

import me.ogq.ocp.sample.model.publicityright.Market
import me.ogq.ocp.sample.model.publicityright.PublicityRight
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.Operator
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EsRequest(
    @Value("\${spring.elasticsearch.index}")
    private val indexName: String,
) {
    fun createWith(
        market: Market,
        query: String,
        page: Int,
        pageSize: Int,
    ): SearchRequest {
        val queryBuilder = QueryBuilders.boolQuery()
        addQueryStringTermInTitleNTags(query, queryBuilder)
        addPublicityRightTermByIsNull(market.publicityRight, queryBuilder)


        val sourceBuilder = SearchSourceBuilder()
        sourceBuilder.query(queryBuilder)
        sourceBuilder.from(page * pageSize)
        sourceBuilder.size(pageSize)

        return SearchRequest(indexName).source(sourceBuilder)
    }

    private fun addQueryStringTermInTitleNTags(query: String, queryBuilder: BoolQueryBuilder) {

        queryBuilder.must(
            QueryBuilders.queryStringQuery("*$query*")
                .field("title")
                .field("tags")
                .lenient(true)
                .defaultOperator(Operator.OR),
        )
    }

    private fun addPublicityRightTermByIsNull(

        publicityRight: PublicityRight?,
        queryBuilder: BoolQueryBuilder,
    ) {

        if (publicityRight == null) {
            queryBuilder.must(QueryBuilders.termQuery("publicity_id", "null"))
            return
        }

        queryBuilder.should(
            QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("publicity_id", "null"))
                .should(QueryBuilders.termQuery("publicity_id", publicityRight.id.toString()))
                .minimumShouldMatch(1),

        )
    }
}
