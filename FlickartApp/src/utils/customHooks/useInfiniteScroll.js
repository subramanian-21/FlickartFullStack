import { useRef, useEffect, useCallback } from 'react';

export function useInfiniteScroll({ onLoadMore, hasMore }) {
  const observer = useRef(null);
  const lastElementRef = useCallback((node) => {
    if (observer.current) observer.current.disconnect(); 

    observer.current = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting && hasMore) {
        onLoadMore()
      }
    })

    if (node) observer.current.observe(node);
  }, [hasMore, onLoadMore])

  useEffect(() => {
    return () => {
      if (observer.current) {
        observer.current.disconnect()
      }
    }
  }, [])

  return lastElementRef
}