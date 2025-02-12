/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.source.mssql;

import static io.airbyte.integrations.source.mssql.MssqlSource.MSSQL_CDC_OFFSET;
import static io.airbyte.integrations.source.mssql.MssqlSource.MSSQL_DB_HISTORY;

import com.fasterxml.jackson.databind.JsonNode;
import io.airbyte.integrations.debezium.CdcSavedInfoFetcher;
import io.airbyte.integrations.source.relationaldb.models.CdcState;
import java.util.Optional;
import io.airbyte.integrations.debezium.internals.AirbyteSchemaHistoryStorage.SchemaHistory;
public class MssqlCdcSavedInfoFetcher implements CdcSavedInfoFetcher {

  private final JsonNode savedOffset;
  private final JsonNode savedSchemaHistory;

  protected MssqlCdcSavedInfoFetcher(final CdcState savedState) {
    final boolean savedStatePresent = savedState != null && savedState.getState() != null;
    this.savedOffset = savedStatePresent ? savedState.getState().get(MSSQL_CDC_OFFSET) : null;
    this.savedSchemaHistory = savedStatePresent ? savedState.getState().get(MSSQL_DB_HISTORY) : null;
  }

  @Override
  public JsonNode getSavedOffset() {
    return savedOffset;
  }

  @Override
  public SchemaHistory<Optional<JsonNode>> getSavedSchemaHistory() {
    return new SchemaHistory<>(Optional.ofNullable(savedSchemaHistory), false);
  }

}
