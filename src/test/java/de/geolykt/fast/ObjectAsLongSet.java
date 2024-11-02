package de.geolykt.fast;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;

final class ObjectAsLongSet implements LongSet {

    @NotNull
    private final Set<Long> backingSet;

    public ObjectAsLongSet(@NotNull Set<Long> backingSet) {
        this.backingSet = backingSet;
    }

    @Override
    public boolean add(long key) {
        return this.backingSet.add(key);
    }

    @Override
    public boolean addAll(Collection<? extends Long> c) {
        return this.backingSet.addAll(c);
    }

    @Override
    public boolean addAll(LongCollection c) {
        LongIterator it = c.longIterator();
        boolean flag = false;
        while (it.hasNext()) {
            flag |= this.add(it.nextLong());
        }
        return flag;
    }

    @Override
    public void clear() {
        this.backingSet.clear();
    }

    @Override
    public boolean contains(long key) {
        return this.backingSet.add(key);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.backingSet.containsAll(c);
    }

    @Override
    public boolean containsAll(LongCollection c) {
        LongIterator it = c.longIterator();
        boolean flag = true;
        while (it.hasNext()) {
            flag &= this.contains(it.nextLong());
        }
        return flag;
    }

    @Override
    public boolean isEmpty() {
        return this.backingSet.isEmpty();
    }

    @Override
    public LongIterator iterator() {
        return new LongIterator() {
            private final Iterator<Long> it = ObjectAsLongSet.this.backingSet.iterator();

            @Override
            public boolean hasNext() {
                return this.it.hasNext();
            }

            @Override
            public long nextLong() {
                return this.it.next().longValue();
            }

            @Override
            public void remove() {
                this.it.remove();
            }
        };
    }

    @Override
    public boolean remove(long k) {
        return this.backingSet.remove(k);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.backingSet.removeAll(c);
    }

    @Override
    public boolean removeAll(LongCollection c) {
        LongIterator it = c.longIterator();
        boolean flag = false;
        while (it.hasNext()) {
            flag |= this.remove(it.nextLong());
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.backingSet.retainAll(c);
    }

    @Override
    public boolean retainAll(LongCollection c) {
        Iterator<Long> it = this.backingSet.iterator();
        boolean flag = false;
        while (it.hasNext()) {
            if (!c.contains(it.next().longValue())) {
                it.remove();
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public int size() {
        return this.backingSet.size();
    }

    @Override
    public Object[] toArray() {
        return this.backingSet.toArray();
    }

    @Override
    public long[] toArray(long[] a) {
        Long[] array = this.backingSet.toArray(new Long[0]);
        long[] out;
        if (array.length < a.length) {
            out = a;
        } else {
            out = new long[array.length];
        }
        for (int i = 0; i < array.length; i++) {
            out[i] = array[i];
        }
        return out;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.backingSet.toArray(a);
    }

    @Override
    public long[] toLongArray() {
        Long[] array = this.backingSet.toArray(new Long[0]);
        long[] out = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            out[i] = array[i];
        }
        return out;
    }
}
