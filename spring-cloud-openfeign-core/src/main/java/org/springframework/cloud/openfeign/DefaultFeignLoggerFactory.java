/*
 * Copyright 2016-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.openfeign;

import feign.Logger;
import feign.slf4j.Slf4jLogger;

/**
 * @author Venil Noronha
 * @author Olga Maciaszek-Sharma
 */
public class DefaultFeignLoggerFactory implements FeignLoggerFactory {

	private final Logger logger;

	public DefaultFeignLoggerFactory(Logger logger) {
		this.logger = logger;
	}

	@Override
	public Logger create(Class<?> type) {
		return this.logger != null ? this.logger : new Slf4jLogger(type);
	}

}