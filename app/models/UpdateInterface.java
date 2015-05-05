package models;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Provides interface for fetching and storing data for user-related content.
 * <p>
 * Each element on the site contains data that needs to be displayed and potentially
 * saved. Data is sent to the client via Json structured data.
 */
public interface UpdateInterface {
  /**
   * Collect data relevant to the associated object for the client.
   *
   * @param data Json object to populate.
   */
  void fetchUpdate(ObjectNode data);

  /**
   * Updates a single value associated to a "score" for a user.
   *
   * @param account Account to associate the update with.
   * @param score   Value to save.
   */
  void update(Account account, int score);
}
