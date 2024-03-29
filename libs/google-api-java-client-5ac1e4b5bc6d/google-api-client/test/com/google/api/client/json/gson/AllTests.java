/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.json.gson;

import junit.framework.TestSuite;

/**
 * All tests for {@link com.google.api.client.json}.
 *
 * @author Yaniv Inbar
 */
public class AllTests extends TestSuite {

  public static TestSuite suite() {
    TestSuite result = new TestSuite(AllTests.class.getPackage().getName());
    result.addTestSuite(GsonGeneratorTest.class);
    result.addTestSuite(GsonParserTest.class);
    return result;
  }
}
