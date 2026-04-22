package it.dmsoft.flowmanager.master.repositories;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.common.model.cards.AgentStatsData;
import it.dmsoft.flowmanager.common.model.cards.TransactionStatsData;

@Repository
public interface FlowLogRepository extends JpaRepository<FlowLog, Long> {
	
    	@Query("""
            SELECT new it.dmsoft.flowmanager.common.model.cards.AgentStatsData(
                f.logAgent,

                SUM(CASE
                    WHEN f.logDirezione = 'O'
                     AND f.logEsito <> 'OK'
                    THEN 1 ELSE 0 END),

                SUM(CASE
                    WHEN f.logDirezione = 'O'
                    THEN 1 ELSE 0 END),

                SUM(CASE
                    WHEN f.logDirezione = 'I'
                     AND f.logEsito <> 'OK'
                    THEN 1 ELSE 0 END),

                SUM(CASE
                    WHEN f.logDirezione = 'I'
                    THEN 1 ELSE 0 END)
            )
            FROM FlowLog  f
            WHERE
                (:agents IS NULL OR f.logAgent IN :agents)
            AND
                f.logTsInizio >= :fromDate
            AND
                (:toDate IS NULL OR f.logTsInizio <= :toDate)
            GROUP BY
                f.logAgent
            ORDER BY
                f.logAgent
        """)
        List<AgentStatsData> aggregateByAgentDirectionStatus(
        	@Param("agents") List<String> agents,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
        );
    	
    	
    	
    	@Query(value = """
			SELECT
			        DATE(f.log_ts_inizio)        AS date,
			        SUM(CASE WHEN f.log_direzione = 'O' THEN 1 ELSE 0 END) AS outbound,
			        SUM(CASE WHEN f.log_direzione = 'O' AND f.log_esito <> 'OK' THEN 1 ELSE 0 END) AS outboundError,
			        SUM(CASE WHEN f.log_direzione = 'I' THEN 1 ELSE 0 END) AS inbound,
			        SUM(CASE WHEN f.log_direzione = 'I' AND f.log_esito <> 'OK' THEN 1 ELSE 0 END) AS inboundError
			    FROM flow_log f
                WHERE
                    f.log_ts_inizio >= :fromDate
                AND
                    f.log_ts_inizio <= :toDate
                GROUP BY
                  DATE(f.log_ts_inizio)
                ORDER BY
                   DATE(f.log_ts_inizio)
            """, nativeQuery = true)
            List<TransactionStatsData> aggregateByDayDirection(
                @Param("fromDate") LocalDateTime fromDate,
                @Param("toDate") LocalDateTime toDate
            );
    	
    	
    	
    	
    	
    	
}
