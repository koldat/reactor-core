/*
 * Copyright (c) 2011-Present VMware Inc. or its affiliates, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reactor.core.publisher;

import org.junit.jupiter.api.Test;
import reactor.test.subscriber.AssertSubscriber;

public class FluxMapNotNullTest {

	@Test
	public void regularMapper() {
		AssertSubscriber<Integer> ts = AssertSubscriber.create();

		Flux.range(1, 5)
				.mapNotNull(v -> v * 2)
				.subscribe(ts);

		ts.assertValues(2, 4, 6, 8, 10)
				.assertComplete()
				.assertNoError();
	}

	@Test
	public void mapperProducingNulls() {
		AssertSubscriber<Integer> ts = AssertSubscriber.create();

		Flux.range(1, 5)
				.mapNotNull(v -> v % 2 == 0 ? v * 2 : null)
				.subscribe(ts);

		ts.assertValues(4, 8)
				.assertComplete()
				.assertNoError();
	}

	@Test
	public void mapperThrowingException() {
		AssertSubscriber<Integer> ts = AssertSubscriber.create();

		Flux.range(1, 5)
				.<Integer>mapNotNull(v -> {throw new RuntimeException();})
				.subscribe(ts);

		ts.assertError(RuntimeException.class);
	}
}
