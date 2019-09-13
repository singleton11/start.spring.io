/*
 * Copyright 2012-2019 the original author or authors.
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

package io.spring.start.site.extension.springdata;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link R2dbcBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class R2dbcBuildCustomizerTests extends AbstractExtensionTests {

	@Test
	void r2dbcWithoutDriverAddsTestUtility() {
		Build build = createBuild("2.2.0.M6");
		build.dependencies().add("data-r2dbc");
		customize(build);
		assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "r2dbc-test-autoconfigure");
	}

	@Test
	void r2dbcWithH2() {
		Build build = createBuild("2.2.0.M6");
		build.dependencies().add("data-r2dbc");
		build.dependencies().add("h2");
		customize(build);
		assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "h2", "r2dbc-h2", "spring-jdbc",
				"r2dbc-test-autoconfigure");
	}

	@Test
	void r2dbcWithMysql() {
		Build build = createBuild("2.2.0.M6");
		build.dependencies().add("data-r2dbc");
		build.dependencies().add("mysql");
		customize(build);
		assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "mysql", "r2dbc-mysql",
				"r2dbc-test-autoconfigure");
		assertThat(build.repositories().ids()).contains("jitpack.io");
	}

	@Test
	void r2dbcWithPostgresql() {
		Build build = createBuild("2.2.0.M6");
		build.dependencies().add("data-r2dbc");
		build.dependencies().add("postgresql");
		customize(build);
		assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "postgresql", "r2dbc-postgresql",
				"r2dbc-test-autoconfigure");
	}

	@Test
	void r2dbcWithSqlserver() {
		Build build = createBuild("2.2.0.M6");
		build.dependencies().add("data-r2dbc");
		build.dependencies().add("sqlserver");
		customize(build);
		assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "sqlserver", "r2dbc-mssql",
				"r2dbc-test-autoconfigure");
	}

	@Test
	void r2dbcWithActuator() {
		Build build = createBuild("2.2.0.M6");
		build.dependencies().add("data-r2dbc");
		build.dependencies().add("actuator");
		customize(build);
		assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "actuator", "r2dbc-actuator-autoconfigure",
				"r2dbc-test-autoconfigure");
	}

	private Build createBuild(String platformVersion) {
		return new MavenBuild(new MetadataBuildItemResolver(getMetadata(), Version.parse(platformVersion)));
	}

	private void customize(Build build) {
		new R2dbcBuildCustomizer().customize(build);
	}

}