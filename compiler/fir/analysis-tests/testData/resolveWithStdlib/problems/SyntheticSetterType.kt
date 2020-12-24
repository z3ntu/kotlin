// FILE: PropertyDescriptorImpl.java

import org.jetbrains.annotations.NotNull;
import java.util.*;

public class PropertyDescriptorImpl implements PropertyDescriptor {
    @Override
    public void setOverriddenDescriptors(@NotNull Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        this.overriddenProperties = (Collection<? extends PropertyDescriptor>) overriddenDescriptors;
    }

    @NotNull
    @Override
    public Collection<? extends PropertyDescriptor> getOverriddenDescriptors() {
        return overriddenProperties != null ? overriddenProperties : Collections.<PropertyDescriptor>emptyList();
    }
}

// FILE: PropertyDescriptor.java

public interface PropertyDescriptor extends CallableMemberDescriptor

// FILE: CallableMemberDescriptor.java

public interface CallableMemberDescriptor

// FILE: test.kt

fun foo(descriptor: PropertyDescriptorImpl) {
    descriptor.overriddenDescriptors = emptyList()
}