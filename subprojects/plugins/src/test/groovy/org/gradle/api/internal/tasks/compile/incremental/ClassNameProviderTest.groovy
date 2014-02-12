/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.tasks.compile.incremental

import org.gradle.test.fixtures.file.TestNameTestDirectoryProvider
import org.junit.Rule
import spock.lang.Specification
import spock.lang.Subject

/**
 * by Szczepan Faber, created at: 1/16/14
 */
class ClassNameProviderTest extends Specification {

    @Rule TestNameTestDirectoryProvider temp = new TestNameTestDirectoryProvider()
    @Subject provider = new ClassNameProvider(temp.createDir("root/dir"))

    def "provides class name"() {
        expect:
        "foo.bar.Foo" == provider.provideName(temp.file("root/dir/foo/bar/Foo.class"))
        "Foo" == provider.provideName(temp.file("root/dir/Foo.class"))
        'Foo$Bar' == provider.provideName(temp.file('root/dir/Foo$Bar.class'))
    }

    def "fails when class is outside of root"() {
        when:
        provider.provideName(temp.file("foo/Foo.class"))
        then:
        thrown(IllegalArgumentException)
    }
}
