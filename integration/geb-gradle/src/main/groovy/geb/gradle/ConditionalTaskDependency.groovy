/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package geb.gradle

import org.gradle.api.Task
import org.gradle.api.tasks.TaskDependency
import org.gradle.api.tasks.TaskProvider

import javax.annotation.Nullable
import java.util.function.Supplier

class ConditionalTaskDependency implements TaskDependency {

    private final Supplier<Boolean> condition
    private final TaskProvider<? extends Task> taskProvider

    ConditionalTaskDependency(Supplier<Boolean> condition, TaskProvider<? extends Task> taskProvider) {
        this.condition = condition
        this.taskProvider = taskProvider
    }

    @Override
    Set<? extends Task> getDependencies(@Nullable Task task) {
        condition.get() ? [taskProvider.get()] : []
    }
}
