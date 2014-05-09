/* ===================================================
 * Copyright © 2013-2014 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

package kamon.play

import play.api.mvc.Action
import play.api.mvc.Results.Ok
import play.api.libs.ws.WS
import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.api.test._
import play.api.test.Helpers._

class WSInstrumentationSpec extends PlaySpec with OneServerPerSuite {

  System.setProperty("config.file", "./kamon-play/src/test/resources/conf/application.conf")

  implicit override lazy val app = FakeApplication(withRoutes = {
    case ("GET", "/async") ⇒ Action { Ok("ok") }
  })

  "the WS instrumentation" should {
    "respond to the Async Action and complete the WS request" in {
      val response = await(WS.url("http://localhost:19001/async").get())

      response.status mustBe (OK)

      //Thread.sleep(2000) //wait to complete the future
    }
  }
}