/*
 * Copyright 2013-2022 the original author or authors.
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

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.lang.Nullable;

/**
 * Feign的上下文，继承NamedContextFactory，为每一个FeignClient维护了一个独有的ApplicationContext。达到隔离的效果
 * <p>
 * A factory that creates instances of feign classes. It creates a Spring
 * ApplicationContext per client name, and extracts the beans that it needs from there.
 *
 * @author Spencer Gibb
 * @author Dave Syer
 * @author Matt King
 * @author Jasbir Singh
 */
public class FeignContext extends NamedContextFactory<FeignClientSpecification> {

	/**
	 * 这里的构造方法最终调用了NamedContextFactory的构造方法，在父类中三个参数含义如下：
	 * defaultConfigType：所有FeignClient的默认配置，所有的子容器都会存在该默认配置，因为在初始化子容器ApplicationContext时会调用org.springframework.context.annotation.AnnotationConfigApplicationContext#register方法，
	 * 		该方法该是Spring框架中的一种注册Bean定义的方式。它允许您在运行时动态地向应用程序上下文中添加Bean定义。该方法接受一个或多个带有@Configuration注释的Java配置类，这些类包含了要注册的Bean定义。
	 * 		当应用程序上下文启动时，它会扫描这些类并将它们转换为Bean定义，然后将这些定义添加到应用程序上下文中。这使得您可以在应用程序运行时动态地添加、修改和删除Bean定义，从而实现更加灵活的应用程序架构
	 * propertySourceName：
	 * propertyName：
	 */
	public FeignContext() {
		super(FeignClientsConfiguration.class, "feign", "feign.client.name");
	}

	@Nullable
	public <T> T getInstanceWithoutAncestors(String name, Class<T> type) {
		try {
			return BeanFactoryUtils.beanOfType(getContext(name), type);
		} catch (BeansException ex) {
			return null;
		}
	}

	@Nullable
	public <T> Map<String, T> getInstancesWithoutAncestors(String name, Class<T> type) {
		return getContext(name).getBeansOfType(type);
	}

	public <T> T getInstance(String contextName, String beanName, Class<T> type) {
		return getContext(contextName).getBean(beanName, type);
	}

}
